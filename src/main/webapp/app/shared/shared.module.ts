import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AppSharedLibsModule, AppSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [AppSharedLibsModule, AppSharedCommonModule],
  declarations: [HasAnyAuthorityDirective],
  exports: [AppSharedCommonModule, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppSharedModule {
  static forRoot() {
    return {
      ngModule: AppSharedModule
    };
  }
}
