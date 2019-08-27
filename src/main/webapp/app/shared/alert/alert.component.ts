import { Component, OnDestroy, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-fedhipster';

@Component({
  selector: 'jhi-alert',
  template: `
    <div class="alerts" role="alert">
      <div *ngFor="let alert of alerts" [ngClass]="setClasses(alert)">
        <div *ngIf="alert && alert.type && alert.msg">
          <pre [innerHTML]="alert.msg"></pre>
          <a (click)="alert.close(alerts)">close</a>
        </div>
      </div>
    </div>
  `
})
export class JhiAlertComponent implements OnInit, OnDestroy {
  alerts: any[];

  constructor(private alertService: JhiAlertService) {}

  ngOnInit() {
    this.alerts = this.alertService.get();
  }

  setClasses(alert) {
    return {
      toast: !!alert.toast,
      [alert.position]: true
    };
  }

  ngOnDestroy() {
    this.alerts = [];
  }
}
