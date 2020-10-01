export class UserImage {
    userId: string;
    image: ImageWrapper;

    public constructor(init?: Partial<UserImage>) {
        Object.assign(this, init);
    }
}

export class ImageWrapper {
    imageInfo: string;
    data: any;

    public constructor(init?: Partial<ImageWrapper>) {
        Object.assign(this, init);
    }

}