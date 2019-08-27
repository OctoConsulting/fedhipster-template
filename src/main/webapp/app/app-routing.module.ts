import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute } from './layouts';

import { headerRoute } from './layouts';

import { DEBUG_INFO_ENABLED } from 'app/app.constants';

const LAYOUT_ROUTES = [headerRoute, ...errorRoute];

console.warn(LAYOUT_ROUTES);

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          loadChildren: './admin/admin.module#AppAdminModule'
        },
        ...LAYOUT_ROUTES
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    )
  ],
  exports: [RouterModule]
})
export class AppAppRoutingModule {}
