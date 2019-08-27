import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {} from 'ng-fedhipster';

import { VERSION } from 'app/app.constants';
import { AccountService, LoginService } from 'app/core';
import { ProfileService } from 'app/layouts/profiles/profile.service';

@Component({
  selector: 'jhi-header',
  templateUrl: './header.component.html',
  styleUrls: ['header.scss']
})
export class HeaderComponent implements OnInit {
  inProduction: boolean;
  languages: any[];
  swaggerEnabled: boolean;
  version: string;
  userName: string;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router
  ) {
    this.version = VERSION ? 'v' + VERSION : '';
  }

  ngOnInit() {
    this.profileService.getProfileInfo().then(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.loginService.login();
  }

  logout() {
    this.loginService.logout();
    this.router.navigate(['']);
  }
}
