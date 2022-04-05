import mongoose from "mongoose";
import Keys from "../keys/Keys";

export const connection = async () => {
  try {
    await mongoose.connect(Keys.MONGO_URL);
    console.log("Connected to MongoDB");
  } catch (error) {
    console.error(error);
  }
};
