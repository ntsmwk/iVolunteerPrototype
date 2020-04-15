import { Component, OnInit, ViewEncapsulation } from "@angular/core";

import { fuseAnimations } from "@fuse/animations";

import { VolunteerProfileService } from "app/main/content/_service/volunteer-profile.service";

@Component({
  selector: "profile-photos-videos",
  templateUrl: "./photos-videos.component.html",
  styleUrls: ["./photos-videos.component.scss"],
  encapsulation: ViewEncapsulation.None,
  animations: fuseAnimations
})
export class ProfilePhotosVideosComponent implements OnInit {
  photosVideos: any;

  constructor(private _profileService: VolunteerProfileService) {}

  ngOnInit(): void {
    // this._profileService.photosVideosOnChanged
    //   .pipe(takeUntil(this._unsubscribeAll))
    //   .subscribe(photosVideos => {
    //     this.photosVideos = photosVideos;
    //   });
  }
}
