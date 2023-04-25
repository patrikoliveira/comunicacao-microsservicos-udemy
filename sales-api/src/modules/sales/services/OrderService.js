import OrderRepository from "../repository/OrderRepository.js";
import { sendMessageToProductStockUpdateQueue } from "../../products/rabbitmq/productStockUpdateSender.js";
import { ACCEPTED, REJECTED, PENDING } from "../status/OrderStatus.js";
import OrderException from "../exception/OrderException.js";
import {
  BAD_REQUEST,
  INTERNAL_SERVER_ERROR,
  SUCCESS,
} from "../../../config/constants/httpStatus.js";
import ProductClient from "../../products/client/ProductClient.js";

class OrderService {
  async createOrder(req) {
    try {
      let orderData = req.body;
      this.validateOrderData(orderData);

      const { authUser } = req;
      const { authorization } = req.headers;

      const order = this.createInitialOrderData(orderData, authUser);

      await this.validateProductStock(order, authorization);

      let createdOrder = await OrderRepository.save(order);
      this.sendMessage(createdOrder);

      return {
        status: SUCCESS,
        createdOrder,
      };
    } catch (err) {
      console.log(err);
      return {
        status: err.status ? err.status : INTERNAL_SERVER_ERROR,
        message: err.message,
      };
    }
  }

  createInitialOrderData(orderData, authUser) {
    return {
      status: PENDING,
      user: authUser,
      createdAt: new Date(),
      updatedAt: new Date(),
      products: orderData.products,
    };
  }

  async updateOrder(orderMessage) {
    try {
      const order = JSON.parse(orderMessage);

      if (order.salesId && order.status) {
        let existingOrder = await OrderRepository.findById(order.salesId);

        if (existingOrder && order.status !== existingOrder.status) {
          existingOrder.status = order.status;
          existingOrder.updatedAt = new Date();
          await OrderRepository.save(existingOrder);
        }
      } else {
        console.warn("The order message was not complete.");
      }
    } catch (err) {
      console.error("Could not parse order message from queue.");
      console.error(err.message);
    }
  }

  validateOrderData(data) {
    if (!data || !data.products) {
      throw new OrderException(BAD_REQUEST, "The products must be informed.");
    }
  }

  async validateProductStock(order, token) {
    let stockIsOk = await ProductClient.checkProductStock(order, token);
    if (!stockIsOk) {
      throw new OrderException(BAD_REQUEST, "The stock is out for products.");
    }
  }

  sendMessage(createdOrder) {
    const message = {
      salesId: createdOrder.id,
      products: createdOrder.products,
    };
    sendMessageToProductStockUpdateQueue(message);
  }

  async findById(req) {
    try {
      const { id } = req.params;
      this.validateInformedId(id);
      const existingOrder = await OrderRepository.findById(id);
      if (!existingOrder) {
        throw new OrderException(BAD_REQUEST, "The order was not found.");
      }

      return {
        status: SUCCESS,
        existingOrder,
      };
    } catch (err) {
      return {
        status: err.status ? err.status : INTERNAL_SERVER_ERROR,
        message: err.message,
      };
    }
  }

  async findAll() {
    try {
      const orders = await OrderRepository.findAll();
      if (!orders) {
        throw new OrderException(BAD_REQUEST, "No orders were found.");
      }

      return {
        status: SUCCESS,
        orders,
      };
    } catch (err) {
      return {
        status: err.status ? err.status : INTERNAL_SERVER_ERROR,
        message: err.message,
      };
    }
  }

  async findByProductId(req) {
    try {
      const { productId } = req.params;
      this.validateInformedProductId(productId);
      const orders = await OrderRepository.findProductId(productId);
      if (!orders) {
        throw new OrderException(BAD_REQUEST, "No orders were found.");
      }

      return {
        status: SUCCESS,
        salesIds: orders.map((order) => order.id),
      };
    } catch (err) {
      return {
        status: err.status ? err.status : INTERNAL_SERVER_ERROR,
        message: err.message,
      };
    }
  }

  validateInformedId(id) {
    if (!id) {
      throw new OrderException(BAD_REQUEST, "The order ID must be informed.");
    }
  }

  validateInformedProductId(id) {
    if (!id) {
      throw new OrderException(
        BAD_REQUEST,
        "The order's product ID must be informed."
      );
    }
  }
}

export default new OrderService();