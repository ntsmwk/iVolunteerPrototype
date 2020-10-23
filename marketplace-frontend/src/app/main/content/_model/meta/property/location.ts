import { isNullOrUndefined } from 'util';

export class Location {
    label: string;
    longLatEnabled: boolean;
    latitude: number;
    longitude: number;


    constructor(label?: string, long?: number, lat?: number) {
        this.label = label;
        this.longLatEnabled = !isNullOrUndefined(long) && !isNullOrUndefined(lat);
        this.longitude = long;
        this.latitude = lat;
    }
}
