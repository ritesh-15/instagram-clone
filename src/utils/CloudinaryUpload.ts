import { v2 as Cloudinary } from "cloudinary";
import Keys from "../keys/keys";

class CloudinaryUpload {
  static init() {
    Cloudinary.config({
      api_key: Keys.CLOUDINARY_API_KEY,
      api_secret: Keys.CLOUDINARY_API_SECRET,
      cloud_name: Keys.CLOUD_NAME,
      secure: true,
    });
  }

  static uploadAsAvatar(file: string) {
    this.init();
    return Cloudinary.uploader.upload(file, {
      transformation: [{ width: 150, height: 150, gravity: "face" }],
    });
  }
}

export default CloudinaryUpload;
