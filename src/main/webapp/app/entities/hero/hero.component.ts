import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-fedhipster';

import { IHero } from 'app/shared/model/hero.model';
import { AccountService } from 'app/core';
import { HeroService } from './hero.service';

@Component({
  selector: 'jhi-hero',
  templateUrl: './hero.component.html'
})
export class HeroComponent implements OnInit, OnDestroy {
  heroes: IHero[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected heroService: HeroService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.heroService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IHero[]>) => res.ok),
          map((res: HttpResponse<IHero[]>) => res.body)
        )
        .subscribe((res: IHero[]) => (this.heroes = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.heroService
      .query()
      .pipe(
        filter((res: HttpResponse<IHero[]>) => res.ok),
        map((res: HttpResponse<IHero[]>) => res.body)
      )
      .subscribe(
        (res: IHero[]) => {
          this.heroes = res;
          this.currentSearch = '';
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHeroes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHero) {
    return item.id;
  }

  registerChangeInHeroes() {
    this.eventSubscriber = this.eventManager.subscribe('heroListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
