import { Component, OnInit } from "@angular/core";
import { LoginService } from "../../../../../../_service/login.service";
import { User } from "../../../../../../_model/user";

@Component({
  selector: "fuse-group-member",
  templateUrl: "./group-member.component.html",
  styleUrls: ["./group-member.component.scss"],
})
export class FuseGroupMemberComponent implements OnInit {
  group = {
    members: [
      { name: "Me", avatar: undefined },
      { name: "Alice Newman", avatar: "assets/images/avatars/alice.jpg" },
      { name: "Andrew Miller", avatar: "assets/images/avatars/andrew.jpg" },
      { name: "Garry Newman", avatar: "assets/images/avatars/garry.jpg" },
      { name: "James Houser", avatar: "assets/images/avatars/james.jpg" },
      { name: "Jane Doe", avatar: "assets/images/avatars/jane.jpg" },
    ],
  };

  constructor(private loginService: LoginService) {}

  ngOnInit() {
    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((user: User) => {
        this.group.members[0].avatar = `assets/images/avatars/${user.username}.jpg`;
      });
  }
}
