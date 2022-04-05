import { ObjectId } from "mongoose";

export interface UserInterface {
  _id: ObjectId | string;
  email: string;
  password: string;
  name: string;
  username: string;
  role: string;
  createdAt: Date;
  updatedAt: Date;
  avatar: {
    url: string;
    publicId: string;
  };
  bio: string;
  resetToken: string;
  resetTokenExpiration: Date;
}
