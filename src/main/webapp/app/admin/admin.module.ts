import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppSharedModule } from 'app/shared';
import {
  JhiJvmMemoryComponent,
  JhiJvmThreadsComponent,
  JhiMetricsHttpRequestComponent,
  JhiMetricsEndpointsRequestsComponent,
  JhiMetricsCacheComponent,
  JhiMetricsDatasourceComponent,
  JhiMetricsSystemComponent,
  JhiMetricsGarbageCollectorComponent,
  JhiThreadModalComponent
} from './metrics';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
  adminState,
  AuditsComponent,
  LogsComponent,
  JhiMetricsMonitoringComponent,
  JhiHealthModalComponent,
  JhiHealthCheckComponent,
  JhiConfigurationComponent,
  JhiDocsComponent,
  JhiGatewayComponent
} from './';

@NgModule({
  imports: [
    AppSharedModule,
    RouterModule.forChild(adminState)
    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
  ],
  declarations: [
    AuditsComponent,
    LogsComponent,
    JhiConfigurationComponent,
    JhiHealthCheckComponent,
    JhiHealthModalComponent,
    JhiDocsComponent,
    JhiGatewayComponent,
    JhiMetricsMonitoringComponent,
    JhiJvmMemoryComponent,
    JhiJvmThreadsComponent,
    JhiMetricsHttpRequestComponent,
    JhiMetricsEndpointsRequestsComponent,
    JhiMetricsCacheComponent,
    JhiMetricsDatasourceComponent,
    JhiMetricsSystemComponent,
    JhiMetricsGarbageCollectorComponent,
    JhiThreadModalComponent
  ],
  entryComponents: [JhiHealthModalComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppAdminModule {}
