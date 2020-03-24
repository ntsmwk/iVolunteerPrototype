import { Badge } from "./badge";

export class BadgeGroup {
  constructor(
    public name: string,
    public progress: number,
    public badges: Badge[]
  ) {}
}
