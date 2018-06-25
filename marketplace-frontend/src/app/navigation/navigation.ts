export const navigation = [
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
        'id': 'available-tasks',
        'title': 'Available',
        'type': 'item',
        'url': '/main/tasks/available'
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
  },
  {
    'id': 'competencies',
    'title': 'Competencies',
    'type': 'collapse',
    'icon': 'widgets',
    'children': [
      {
        'id': 'my-competencies',
        'title': 'My Competencies',
        'type': 'item',
        'url': '/main/competencies/my'
      },
      {
        'id': 'all-competencies',
        'title': 'All Competencies',
        'type': 'item',
        'url': '/main/competencies/all'
      }
    ]
  }
];
