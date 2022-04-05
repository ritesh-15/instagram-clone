import { ObjectId } from "mongoose";
import jwt from "jsonwebtoken";
import Keys from "../keys/keys";

class JWTTokens {
  static generateTokens(userId: string | ObjectId) {
    const accessToken = jwt.sign({ _id: userId }, Keys.ACCESS_TOKEN_SECRET, {
      expiresIn: "25m",
    });

    const refreshToken = jwt.sign({ _id: userId }, Keys.REFRESH_TOKEN_SECRET, {
      expiresIn: "7d",
    });

    return { accessToken, refreshToken };
  }

  static validateRefreshToken(token: string) {
    return jwt.verify(token, Keys.REFRESH_TOKEN_SECRET);
  }
}

export default JWTTokens;
