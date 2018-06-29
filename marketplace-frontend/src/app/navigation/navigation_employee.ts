export const navigation_employee = [
    {
      'id': 'dashboard',
      'title': 'Dashboard',
      'type': 'item',
      'icon': 'dashboard',
      'url': '/main/dashboard'
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
            'id': 'create-tasks',
            'title': 'Create',
            'type': 'item',
            'url': '/main/tasks/all'
          },
        {
          'id': 'all-tasks',
          'title': 'All',
          'type': 'item',
          'url': '/main/tasks/all'
        },
        {
          'id': 'upcomming-tasks',
          'title': 'Upcomming',
          'type': 'item',
          'url': '/main/tasks/upcomming'
        },
        {
          'id': 'running-tasks',
          'title': 'Running',
          'type': 'item',
          'url': '/main/tasks/running'
        },
        {
          'id': 'finished-tasks',
          'title': 'Finished',
          'type': 'item',
          'url': '/main/tasks/finished'
        }
      ]
    }
  ];
  