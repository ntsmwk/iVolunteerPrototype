import {Injectable} from '@angular/core';
import {isNullOrUndefined} from 'util';

@Injectable()
export class ArrayService {

  concat(array1: any[], array2: any[]): any[] {
    const commonArray = [].concat(array1);
    array2.forEach((item: any) => {
      if (!this.contains(commonArray, item)) {
        commonArray.push(item);
      }
    });
    return commonArray;
  }

  contains(array: any[], item: any) {
    const containedItem = array.find((current: any) => current.id === item.id);
    return !isNullOrUndefined(containedItem);
  }

}
