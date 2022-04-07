import multer from "multer";
import path from "path";
import { v4 as uuid4 } from "uuid";

const storage = multer.diskStorage({
  filename: (req, file, cb) => {
    const name = `${uuid4()}${path.extname(file.originalname)}`;
    cb(null, name);
  },
  destination: (req, file, cb) => {
    const dest = path.join(__dirname, "../public/uploads");
    cb(null, dest);
  },
});

export const uploader = multer({ storage });
