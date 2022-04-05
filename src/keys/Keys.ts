import { config } from "dotenv";
config();

class Keys {
  static readonly MONGO_URL = process.env.MONGO_URL!!;
  static readonly ACCESS_TOKEN_SECRET = process.env.ACCESS_TOKEN_SECRET!!;
  static readonly REFRESH_TOKEN_SECRET = process.env.REFRESH_TOKEN_SECRET!!;
  static readonly SEND_GRID_API_KEY = process.env.SEND_GRID_API_KEY!!;
  static readonly SEND_GRID_EMAIL = process.env.SEND_GRID_EMAIL!!;
  static readonly HASH_SECRET = process.env.HASH_SECRET!!;
  static readonly CLOUDINARY_API_KEY = process.env.CLOUDINARY_API_KEY!!;
  static readonly CLOUDINARY_API_SECRET = process.env.CLOUDINARY_API_SECRET!!;
  static readonly CLOUD_NAME = process.env.CLOUD_NAME!!;
}

export default Keys;
