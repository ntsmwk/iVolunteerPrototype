export class ImageWrapper {
    imageInfo: string;
    data: any;

    public constructor(init?: Partial<ImageWrapper>) {
        Object.assign(this, init);
    }

}