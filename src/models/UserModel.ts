import { Schema, model } from "mongoose";
import { UserInterface } from "../interfaces/UserInterface";

const userSchema = new Schema<UserInterface>(
  {
    email: {
      type: String,
      required: true,
      unique: true,
    },
    password: {
      type: String,
      select: false,
      default: "",
    },
    name: {
      type: String,
      default: "",
    },
    username: {
      type: String,
      default: () => Date.now().toString(),
      unique: true,
    },
    role: {
      type: String,
      default: "user",
    },
    bio: {
      type: String,
      default: "",
    },
    avatar: {
      url: {
        type: String,
        default: "",
      },
      publicId: {
        type: String,
        default: "",
      },
    },
    resetToken: {
      type: String,
      default: "",
    },
    resetTokenExpiration: {
      type: Date,
      default: () => Date.now(),
    },
    isVerified: {
      type: Boolean,
      default: false,
    },
  },
  {
    timestamps: true,
  }
);

const User = model<UserInterface>("users", userSchema);

export default User;
