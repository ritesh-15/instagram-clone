import { Schema, model, ObjectId } from "mongoose";

interface TokenInterface {
  userId: ObjectId | string;
  refreshToken: string;
  createdAt: Date;
  updatedAt: Date;
}

const tokenSchema = new Schema<TokenInterface>(
  {
    userId: {
      type: Schema.Types.ObjectId,
      required: true,
      ref: "users",
    },
    refreshToken: {
      type: String,
      required: true,
    },
  },
  {
    timestamps: true,
  }
);

const Token = model<TokenInterface>("tokens", tokenSchema);

export default Token;
