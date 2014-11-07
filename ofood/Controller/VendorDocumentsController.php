<?php
App::uses('AppController', 'Controller');
/**
 * VendorDocuments Controller
 *
 * @property VendorDocument $VendorDocument
 * @property PaginatorComponent $Paginator
 * @property SessionComponent $Session
 */
class VendorDocumentsController extends AppController {

/**
 * Components
 *
 * @var array
 */
	public $components = array('Paginator', 'Session');
        
        public function api_index(){
            $VendorDocument = $this->VendorDocument->find('all');
            $this->set(array(
                'data' => $VendorDocument,
                '_serialize' => array('data')
            ));
        }

/**
 * index method
 *
 * @return void
 */
	public function index() {
		$this->VendorDocument->recursive = 0;
		$this->set('vendorDocuments', $this->Paginator->paginate());
	}

/**
 * view method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function view($id = null) {
		if (!$this->VendorDocument->exists($id)) {
			throw new NotFoundException(__('Invalid vendor document'));
		}
		$options = array('conditions' => array('VendorDocument.' . $this->VendorDocument->primaryKey => $id));
		$this->set('vendorDocument', $this->VendorDocument->find('first', $options));
	}

/**
 * add method
 *
 * @return void
 */
	public function add() {
		if ($this->request->is('post')) {
			$this->VendorDocument->create();
			if ($this->VendorDocument->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor document has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor document could not be saved. Please, try again.'));
			}
		}
		$vendors = $this->VendorDocument->Vendor->find('list');
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
		if (!$this->VendorDocument->exists($id)) {
			throw new NotFoundException(__('Invalid vendor document'));
		}
		if ($this->request->is(array('post', 'put'))) {
			if ($this->VendorDocument->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor document has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor document could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('VendorDocument.' . $this->VendorDocument->primaryKey => $id));
			$this->request->data = $this->VendorDocument->find('first', $options);
		}
		$vendors = $this->VendorDocument->Vendor->find('list');
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
		$this->VendorDocument->id = $id;
		if (!$this->VendorDocument->exists()) {
			throw new NotFoundException(__('Invalid vendor document'));
		}
		$this->request->allowMethod('post', 'delete');
		if ($this->VendorDocument->delete()) {
			$this->Session->setFlash(__('The vendor document has been deleted.'));
		} else {
			$this->Session->setFlash(__('The vendor document could not be deleted. Please, try again.'));
		}
		return $this->redirect(array('action' => 'index'));
	}

/**
 * admin_index method
 *
 * @return void
 */
	public function admin_index() {
		$this->VendorDocument->recursive = 0;
		$this->set('vendorDocuments', $this->Paginator->paginate());
	}

/**
 * admin_view method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_view($id = null) {
		if (!$this->VendorDocument->exists($id)) {
			throw new NotFoundException(__('Invalid vendor document'));
		}
		$options = array('conditions' => array('VendorDocument.' . $this->VendorDocument->primaryKey => $id));
		$this->set('vendorDocument', $this->VendorDocument->find('first', $options));
	}

/**
 * admin_add method
 *
 * @return void
 */
	public function admin_add() {
		if ($this->request->is('post')) {
			$this->VendorDocument->create();
			if ($this->VendorDocument->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor document has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor document could not be saved. Please, try again.'));
			}
		}
		$vendors = $this->VendorDocument->Vendor->find('list');
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
		if (!$this->VendorDocument->exists($id)) {
			throw new NotFoundException(__('Invalid vendor document'));
		}
		if ($this->request->is(array('post', 'put'))) {
			if ($this->VendorDocument->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor document has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor document could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('VendorDocument.' . $this->VendorDocument->primaryKey => $id));
			$this->request->data = $this->VendorDocument->find('first', $options);
		}
		$vendors = $this->VendorDocument->Vendor->find('list');
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
		$this->VendorDocument->id = $id;
		if (!$this->VendorDocument->exists()) {
			throw new NotFoundException(__('Invalid vendor document'));
		}
		$this->request->allowMethod('post', 'delete');
		if ($this->VendorDocument->delete()) {
			$this->Session->setFlash(__('The vendor document has been deleted.'));
		} else {
			$this->Session->setFlash(__('The vendor document could not be deleted. Please, try again.'));
		}
		return $this->redirect(array('action' => 'index'));
	}
}
