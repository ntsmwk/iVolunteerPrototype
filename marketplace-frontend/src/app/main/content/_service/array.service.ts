import {Injectable} from '@angular/core';
import {isNullOrUndefined} from 'util';

@Injectable({
  providedIn: 'root'
})
export class ArrayService {

  contains(values: any[], current: any) {
    if (isNullOrUndefined(values)) {
      return false;
    }

    return !isNullOrUndefined(values.find((value) => value.id === current.id));
  }

  removeAll(all: any[], removeables: any[]): any[] {
    removeables.forEach((removeable: any) => {
      all = all.filter((current: any) => current.id !== removeable.id);
    });
    return all;
  }

}
