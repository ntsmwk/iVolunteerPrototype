export class Feedback {   
    id: string;
    marketplaceId: string;
    timestamp: Date;

    name: string;
    description: string;
    feedbackType: FeedbackType;
    feedbackValue: number;    
    iVolunteerObjectIds: string[];
    
    userid: string;
    issuerId: string;
}

export enum FeedbackType {
    KUDOS = 'KUDOS', STARRATING = 'STARRATING'
}