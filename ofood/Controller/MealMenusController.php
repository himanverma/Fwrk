<?php
App::uses('AppController', 'Controller');
/**
 * MealMenus Controller
 *
 * @property MealMenu $MealMenu
 * @property PaginatorComponent $Paginator
 * @property SessionComponent $Session
 */
class MealMenusController extends AppController {

/**
 * Components
 *
 * @var array
 */
	public $components = array('Paginator', 'Session');
        
        
        public function api_index(){
            $MealMenu = $this->MealMenu->find('all');
            $this->set(array(
                'data' => $MealMenu,
                '_serialize' => array('data')
            ));
        }

/**
 * index method
 *
 * @return void
 */
	public function index() {
		$this->MealMenu->recursive = 0;
		$this->set('mealMenus', $this->Paginator->paginate());
	}

/**
 * view method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function view($id = null) {
		if (!$this->MealMenu->exists($id)) {
			throw new NotFoundException(__('Invalid meal menu'));
		}
		$options = array('conditions' => array('MealMenu.' . $this->MealMenu->primaryKey => $id));
		$this->set('mealMenu', $this->MealMenu->find('first', $options));
	}

/**
 * add method
 *
 * @return void
 */
	public function add() {
		if ($this->request->is('post')) {
			$this->MealMenu->create();
			if ($this->MealMenu->save($this->request->data)) {
				$this->Session->setFlash(__('The meal menu has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The meal menu could not be saved. Please, try again.'));
			}
		}
		$vendorDays = $this->MealMenu->VendorDay->find('list');
		$recipes = $this->MealMenu->Recipe->find('list');
		$this->set(compact('vendorDays', 'recipes'));
	}

/**
 * edit method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function edit($id = null) {
		if (!$this->MealMenu->exists($id)) {
			throw new NotFoundException(__('Invalid meal menu'));
		}
		if ($this->request->is(array('post', 'put'))) {
			if ($this->MealMenu->save($this->request->data)) {
				$this->Session->setFlash(__('The meal menu has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The meal menu could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('MealMenu.' . $this->MealMenu->primaryKey => $id));
			$this->request->data = $this->MealMenu->find('first', $options);
		}
		$vendorDays = $this->MealMenu->VendorDay->find('list');
		$recipes = $this->MealMenu->Recipe->find('list');
		$this->set(compact('vendorDays', 'recipes'));
	}

/**
 * delete method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function delete($id = null) {
		$this->MealMenu->id = $id;
		if (!$this->MealMenu->exists()) {
			throw new NotFoundException(__('Invalid meal menu'));
		}
		$this->request->allowMethod('post', 'delete');
		if ($this->MealMenu->delete()) {
			$this->Session->setFlash(__('The meal menu has been deleted.'));
		} else {
			$this->Session->setFlash(__('The meal menu could not be deleted. Please, try again.'));
		}
		return $this->redirect(array('action' => 'index'));
	}

/**
 * admin_index method
 *
 * @return void
 */
	public function admin_index() {
		$this->MealMenu->recursive = 0;
		$this->set('mealMenus', $this->Paginator->paginate());
	}

/**
 * admin_view method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_view($id = null) {
		if (!$this->MealMenu->exists($id)) {
			throw new NotFoundException(__('Invalid meal menu'));
		}
		$options = array('conditions' => array('MealMenu.' . $this->MealMenu->primaryKey => $id));
		$this->set('mealMenu', $this->MealMenu->find('first', $options));
	}

/**
 * admin_add method
 *
 * @return void
 */
	public function admin_add() {
		if ($this->request->is('post')) {
			$this->MealMenu->create();
			if ($this->MealMenu->save($this->request->data)) {
				$this->Session->setFlash(__('The meal menu has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The meal menu could not be saved. Please, try again.'));
			}
		}
		$vendorDays = $this->MealMenu->VendorDay->find('list');
		$recipes = $this->MealMenu->Recipe->find('list');
		$this->set(compact('vendorDays', 'recipes'));
	}

/**
 * admin_edit method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_edit($id = null) {
		if (!$this->MealMenu->exists($id)) {
			throw new NotFoundException(__('Invalid meal menu'));
		}
		if ($this->request->is(array('post', 'put'))) {
			if ($this->MealMenu->save($this->request->data)) {
				$this->Session->setFlash(__('The meal menu has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The meal menu could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('MealMenu.' . $this->MealMenu->primaryKey => $id));
			$this->request->data = $this->MealMenu->find('first', $options);
		}
		$vendorDays = $this->MealMenu->VendorDay->find('list');
		$recipes = $this->MealMenu->Recipe->find('list');
		$this->set(compact('vendorDays', 'recipes'));
	}

/**
 * admin_delete method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_delete($id = null) {
		$this->MealMenu->id = $id;
		if (!$this->MealMenu->exists()) {
			throw new NotFoundException(__('Invalid meal menu'));
		}
		$this->request->allowMethod('post', 'delete');
		if ($this->MealMenu->delete()) {
			$this->Session->setFlash(__('The meal menu has been deleted.'));
		} else {
			$this->Session->setFlash(__('The meal menu could not be deleted. Please, try again.'));
		}
		return $this->redirect(array('action' => 'index'));
	}
}
