import { Injectable } from "@angular/core";
import { isNullOrUndefined } from "util";

@Injectable({
  providedIn: "root",
})
export class ArrayService {
  concat(array1: any[], array2: any[]): any[] {
    const commonArray = [].concat(array1);
    if (array2) {
      array2.forEach((item: any) => {
        if (!this.contains(commonArray, item)) {
          commonArray.push(item);
        }
      });
    }
    return commonArray;
  }

  contains(values: any[], current: any) {
    if (isNullOrUndefined(values)) {
      return false;
    }
    return !isNullOrUndefined(
      values.find((value) => (value ? value.id === current.id : false))
    );
  }

  removeAll(all: any[], removeables: any[]): any[] {
    removeables.forEach((removeable: any) => {
      all = all.filter((current: any) => current.id !== removeable.id);
    });
    return all;
  }
}
