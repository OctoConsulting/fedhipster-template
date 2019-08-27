import { NgModule } from '@angular/core';

import { AppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';
import { ModalComponent } from 'app/shared/modal/modal.component';
import { A11yModule } from '@angular/cdk/a11y';

@NgModule({
  imports: [AppSharedLibsModule, A11yModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent, ModalComponent],
  exports: [AppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent, ModalComponent]
})
export class AppSharedCommonModule {}
