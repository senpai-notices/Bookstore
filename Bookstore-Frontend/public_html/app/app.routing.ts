import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { IndexComponent } from './components/index/index.component'
import { DashboardComponent } from './components/dashboard/dashboard.component'

const appRoutes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: '',
    component: IndexComponent
  },
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);