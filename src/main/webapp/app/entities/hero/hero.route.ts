import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-fedhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Hero } from 'app/shared/model/hero.model';
import { HeroService } from './hero.service';
import { HeroComponent } from './hero.component';
import { HeroDetailComponent } from './hero-detail.component';
import { HeroUpdateComponent } from './hero-update.component';
import { IHero } from 'app/shared/model/hero.model';

@Injectable({ providedIn: 'root' })
export class HeroResolve implements Resolve<IHero> {
  constructor(private service: HeroService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHero> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Hero>) => response.ok),
        map((hero: HttpResponse<Hero>) => hero.body)
      );
    }
    return of(new Hero());
  }
}

export const heroRoute: Routes = [
  {
    path: '',
    component: HeroComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Heroes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HeroDetailComponent,
    resolve: {
      hero: HeroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Heroes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HeroUpdateComponent,
    resolve: {
      hero: HeroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Heroes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HeroUpdateComponent,
    resolve: {
      hero: HeroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Heroes'
    },
    canActivate: [UserRouteAccessService]
  }
];
