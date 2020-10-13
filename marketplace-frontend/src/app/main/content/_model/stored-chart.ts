export class StoredChart {
  id: string;
  userId: string;
  title: string;
  type: string;
  data: string;

  constructor(title: string, type: string, data: string, userId: string) {
    this.title = title;
    this.type = type;
    this.data = data;
    this.userId = userId;
  }
}