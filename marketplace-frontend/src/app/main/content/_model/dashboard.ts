import {Dashlet} from './dashlet';
import {Participant} from './participant';

export class Dashboard {
  id: string;
  name: string;
  user: Participant;
  dashlets: Array<Dashlet>;
}
