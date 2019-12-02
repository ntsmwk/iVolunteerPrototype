import { MediaChange, MediaObserver } from '@angular/flex-layout';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class FuseMatchMediaService
{
    activeMediaQuery: string;
    onMediaChange: BehaviorSubject<string> = new BehaviorSubject<string>('');

    constructor(private observableMedia: MediaObserver)
    {
        this.activeMediaQuery = '';

        this.observableMedia.media$.subscribe((change: MediaChange) => {
            if ( this.activeMediaQuery !== change.mqAlias )
            {
                this.activeMediaQuery = change.mqAlias;
                this.onMediaChange.next(change.mqAlias);
            }
        });
    }
}
