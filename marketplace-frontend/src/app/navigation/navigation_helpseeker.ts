export const navigation_helpseeker = [
  {
    'id': 'projects',
    'title': 'Project',
    'type': 'group',
    'children': [
      {
        'id': 'project-form',
        'title': 'Form',
        'type': 'item',
        'url': '/main/project-form'
      },
      {
        'id': 'project-list',
        'title': 'Overview',
        'type': 'item',
        'url': '/main/projects/all'
      }
    ]
  },
  {
    'id': 'tasks',
    'title': 'Task',
    'type': 'group',
    'children': [
      {
        'id': 'task-form',
        'title': 'Form',
        'type': 'item',
        'url': '/main/task-form'
      },
      {
        'id': 'task-detail',
        'title': 'Detail',
        'type': 'item',
        'url': '/main/task'
      },
      {
        'id': 'task-list',
        'title': 'Overview',
        'type': 'item',
        'url': '/main/tasks/all'
      }
    ]
  },
  {
    'id': 'taskTemplates',
    'title': 'Task Templates',
    'type': 'group',
    'children': [
      {
        'id': 'task-template-form',
        'title': 'Form',
        'type': 'item',
        'url': '/main/task-template-form'
      },
      {
        'id': 'task-template-list',
        'title': 'Overview',
        'type': 'item',
        'url': '/main/task-templates/all'
      }
    ]
  }
];
