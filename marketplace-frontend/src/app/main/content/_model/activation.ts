import { User } from './user';

export class ActivationLinkClickedResponse {
    pendingActivation: PendingActivation;
    activationResponse: ActivationResponse;
    user: User;
}

export class PendingActivation {
    activationId: string;
    userId: string;
    timestamp: Date;
}

export enum ActivationResponse {
    SUCCESS = 'SUCCESS',
    FAILED = 'FAILED',
    EXPIRED = 'EXPIRED'
}


