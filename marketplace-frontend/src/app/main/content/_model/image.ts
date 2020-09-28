export class Image {
  id: string;
  image: ImageWrapper;

  public constructor(init?: Partial<ImageWrapper>) {
    Object.assign(this, init);
  }
}

export class UserImage extends Image {
  userId: string;
}

export class ImageWrapper {
  imageInfo: string;
  data: any;

  public constructor(init?: Partial<ImageWrapper>) {
    Object.assign(this, init);
  }
}
