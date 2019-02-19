import {Participant} from './participant'

export class Group {
    constructor(
        public id: string,
        public description: string,
        public autoJoin: boolean
    ) {

    }
}
