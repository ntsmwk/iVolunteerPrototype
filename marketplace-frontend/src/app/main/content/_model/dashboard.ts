import {Dashlet} from './dashlet';
import {Participant} from './participant';

export class Dashboard {
  id: string;
  name: string;
  dashlets: Array<Dashlet>;
}
