export class Participant {
  id: string;
  username: string;

  firstname: string;
  lastname: string;
  middlename: string;
  nickname: string;
}

export type ParticipantRole = "VOLUNTEER" | "HELP_SEEKER" | "ADMIN" | "RECRUITER" | "FLEXPROD";
