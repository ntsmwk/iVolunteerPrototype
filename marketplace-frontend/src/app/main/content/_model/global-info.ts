import { Participant, ParticipantRole } from "./participant";
import { Tenant } from "./tenant";
import { Marketplace } from "./marketplace";

export class GlobalInfo {
  participant: Participant;
  participantRole: ParticipantRole;
  marketplace: Marketplace;
  tenants: Tenant[];
}
