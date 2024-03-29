import { SpyObject } from './spyobject';
import { JhiAlertService, JhiAlert } from 'ng-fedhipster';

export class MockAlertService extends SpyObject {
  constructor() {
    super(JhiAlertService);
  }
  addAlert(alertOptions: JhiAlert) {
    return alertOptions;
  }
}
