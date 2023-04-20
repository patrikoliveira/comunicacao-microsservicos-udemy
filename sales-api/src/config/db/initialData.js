import Order from "../../modules/sales/model/Order.js";

export async function createInitialData() {
  await Order.collection.drop();
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
  });
}
