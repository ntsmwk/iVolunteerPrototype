export const navigation = [
  {
    'id': 'dashboard',
    'title': 'Dashboard',
    'type': 'item',
    'icon': 'dashboard',
    'url': '/main'
  },
  {
    'id': 'profile',
    'title': 'Profile',
    'type': 'item',
    'icon': 'person',
    'url': '/main/profile'
  },
  {
    'id': 'calendar',
    'title': 'Calendar',
    'type': 'item',
    'icon': 'today',
    'url': '/main/calendar'
  },
  {
    'id': 'tasks',
    'title': 'Tasks',
    'type': 'collapse',
    'icon': 'work',
    'children': [
      {
        'id': 'available-tasks',
        'title': 'Available',
        'type': 'item',
        'url': '/apps/tasks?status=AVAILABLE'
      },
      {
        'id': 'upcomming-tasks',
        'title': 'Upcomming',
        'type': 'item',
        'url': '/apps/tasks?status=UPCOMMING'
      },
      {
        'id': 'running-tasks',
        'title': 'Running',
        'type': 'item',
        'url': '/apps/tasks?status=RUNNING'
      },
      {
        'id': 'finished-tasks',
        'title': 'Finished',
        'type': 'item',
        'url': '/apps/tasks?status=FINISHED'
      }
    ]
  },
  {
    'id': 'competencies',
    'title': 'Competencies',
    'type': 'item',
    'icon': 'widgets',
    'url': '/main/competencies'
  }
];

