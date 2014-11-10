<?php
App::uses('AppController', 'Controller');
/**
 * VendorDays Controller
 *
 * @property VendorDay $VendorDay
 * @property PaginatorComponent $Paginator
 * @property SessionComponent $Session
 */
class VendorDaysController extends AppController {

/**
 * Components
 *
 * @var array
 */
	public $components = array('Paginator', 'Session');
        
        public function api_index(){
            $VendorDay = $this->VendorDay->find('all');
            $this->set(array(
                'data' => $VendorDay,
                '_serialize' => array('data')
            ));
        }

/**
 * index method
 *
 * @return void
 */
	public function index() {
		$this->VendorDay->recursive = 0;
		$this->set('vendorDays', $this->Paginator->paginate());
	}

/**
 * view method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function view($id = null) {
		if (!$this->VendorDay->exists($id)) {
			throw new NotFoundException(__('Invalid vendor day'));
		}
		$options = array('conditions' => array('VendorDay.' . $this->VendorDay->primaryKey => $id));
		$this->set('vendorDay', $this->VendorDay->find('first', $options));
	}

/**
 * add method
 *
 * @return void
 */
	public function add() {
		if ($this->request->is('post')) {
			$this->VendorDay->create();
			if ($this->VendorDay->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor day has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor day could not be saved. Please, try again.'));
			}
		}
		$vendors = $this->VendorDay->Vendor->find('list');
		$this->set(compact('vendors'));
	}

/**
 * edit method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function edit($id = null) {
		if (!$this->VendorDay->exists($id)) {
			throw new NotFoundException(__('Invalid vendor day'));
		}
		if ($this->request->is(array('post', 'put'))) {
			if ($this->VendorDay->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor day has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor day could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('VendorDay.' . $this->VendorDay->primaryKey => $id));
			$this->request->data = $this->VendorDay->find('first', $options);
		}
		$vendors = $this->VendorDay->Vendor->find('list');
		$this->set(compact('vendors'));
	}

/**
 * delete method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function delete($id = null) {
		$this->VendorDay->id = $id;
		if (!$this->VendorDay->exists()) {
			throw new NotFoundException(__('Invalid vendor day'));
		}
		$this->request->allowMethod('post', 'delete');
		if ($this->VendorDay->delete()) {
			$this->Session->setFlash(__('The vendor day has been deleted.'));
		} else {
			$this->Session->setFlash(__('The vendor day could not be deleted. Please, try again.'));
		}
		return $this->redirect(array('action' => 'index'));
	}

/**
 * admin_index method
 *
 * @return void
 */
	public function admin_index() {
		$this->VendorDay->recursive = 0;
		$this->set('vendorDays', $this->Paginator->paginate());
	}

/**
 * admin_view method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_view($id = null) {
		if (!$this->VendorDay->exists($id)) {
			throw new NotFoundException(__('Invalid vendor day'));
		}
		$options = array('conditions' => array('VendorDay.' . $this->VendorDay->primaryKey => $id));
		$this->set('vendorDay', $this->VendorDay->find('first', $options));
	}

/**
 * admin_add method
 *
 * @return void
 */
	public function admin_add() {
		if ($this->request->is('post')) {
			$this->VendorDay->create();
			if ($this->VendorDay->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor day has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor day could not be saved. Please, try again.'));
			}
		}
		$vendors = $this->VendorDay->Vendor->find('list');
		$this->set(compact('vendors'));
	}

/**
 * admin_edit method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_edit($id = null) {
		if (!$this->VendorDay->exists($id)) {
			throw new NotFoundException(__('Invalid vendor day'));
		}
		if ($this->request->is(array('post', 'put'))) {
			if ($this->VendorDay->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor day has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor day could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('VendorDay.' . $this->VendorDay->primaryKey => $id));
			$this->request->data = $this->VendorDay->find('first', $options);
		}
		$vendors = $this->VendorDay->Vendor->find('list');
		$this->set(compact('vendors'));
	}

/**
 * admin_delete method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_delete($id = null) {
		$this->VendorDay->id = $id;
		if (!$this->VendorDay->exists()) {
			throw new NotFoundException(__('Invalid vendor day'));
		}
		$this->request->allowMethod('post', 'delete');
		if ($this->VendorDay->delete()) {
			$this->Session->setFlash(__('The vendor day has been deleted.'));
		} else {
			$this->Session->setFlash(__('The vendor day could not be deleted. Please, try again.'));
		}
		return $this->redirect(array('action' => 'index'));
	}
}
