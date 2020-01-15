export class StoredChart {
    id: string;
    title: string;
    type: string;
    data: string;

    constructor(title: string, type: string, data: string){
        this.title = title; 
        this.type = type;
        this.data = data;
      }
}