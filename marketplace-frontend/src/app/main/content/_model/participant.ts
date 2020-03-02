export class Participant {
  id: string;
  username: string;
  password: string;

  firstname: string;
  lastname: string;
  middlename: string;
  nickname: string;

  position: string;
}

export type ParticipantRole =
  | "VOLUNTEER"
  | "HELP_SEEKER"
  | "ADMIN"
  | "RECRUITER"
  | "FLEXPROD";

export class UserImagePath {
  userId: string;
  imagePath: string;
}
