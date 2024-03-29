import { v4 as uuidv4 } from "uuid";
import Order from "../../modules/sales/model/Order.js";

export async function createInitialData() {
  try {
    let existingData = await Order.find();
    if (existingData && existingData.length > 0) {
      console.info("Remove existing data...");
      await Order.collection.drop();
    }

    await Order.create({
      products: [
        {
          productId: 1001,
          quantity: 3,
        },
        {
          productId: 1002,
          quantity: 1,
        },
        {
          productId: 1003,
          quantity: 1,
        },
      ],
      user: {
        id: "79335a8f-4427-4bc4-88a0-588ac0eaede3",
        name: "User Test",
        email: "usertest@gmail.com",
      },
      status: "APROVED",
      createdAt: new Date(),
      updatedAt: new Date(),
      transactionid: uuidv4(),
      serviceid: uuidv4(),
    });
    await Order.create({
      products: [
        {
          productId: 1001,
          quantity: 4,
        },
        {
          productId: 1003,
          quantity: 2,
        },
      ],
      user: {
        id: "94680c75-719e-41eb-b530-891a6b06f85d",
        name: "User Test 2",
        email: "usertest2@gmail.com",
      },
      status: "REJECT",
      createdAt: new Date(),
      updatedAt: new Date(),
      transactionid: uuidv4(),
      serviceid: uuidv4(),
    });

    let initialData = await Order.find();
    console.info(
      `Initial data was created: ${JSON.stringify(initialData, undefined, 4)}`
    );
  } catch (error) {
    console.error(`Error creating initial data`);
  }
}
