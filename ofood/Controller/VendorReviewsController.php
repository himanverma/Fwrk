<?php
App::uses('AppController', 'Controller');
/**
 * VendorReviews Controller
 *
 * @property VendorReview $VendorReview
 * @property PaginatorComponent $Paginator
 * @property SessionComponent $Session
 */
class VendorReviewsController extends AppController {

/**
 * Components
 *
 * @var array
 */
	public $components = array('Paginator', 'Session');
        
/*------------------------------------------ Web-Services-Start----------------------------------------*/
        public function api_index() {
            $vendorReview = $this->VendorReview->find('all');
            $this->set(array(
                'data' => $vendorReview,
                '_serialize' => array('data')
            ));
        }
        
        public function api_view($id = null) {
		if (!$this->VendorReview->exists($id)) {
			throw new NotFoundException(__('Invalid VendorReview'));
		}
		$options = array('conditions' => array('VendorReview.' . $this->VendorReview->primaryKey => $id));
                $this->set(array(
                    'data' => $this->VendorReview->find('first', $options),
                    '_serialize' => array('data')
                ));
	}
        
/*------------------------------------------ Web-Services-End----------------------------------------*/

/**
 * index method
 *
 * @return void
 */
	public function index() {
		$this->VendorReview->recursive = 0;
		$this->set('vendorReviews', $this->Paginator->paginate());
	}

/**
 * view method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function view($id = null) {
		if (!$this->VendorReview->exists($id)) {
			throw new NotFoundException(__('Invalid vendor review'));
		}
		$options = array('conditions' => array('VendorReview.' . $this->VendorReview->primaryKey => $id));
		$this->set('vendorReview', $this->VendorReview->find('first', $options));
	}

/**
 * add method
 *
 * @return void
 */
	public function add() {
		if ($this->request->is('post')) {
			$this->VendorReview->create();
			if ($this->VendorReview->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor review has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor review could not be saved. Please, try again.'));
			}
		}
		$customers = $this->VendorReview->Customer->find('list');
		$vendors = $this->VendorReview->Vendor->find('list');
		$this->set(compact('customers', 'vendors'));
	}

/**
 * edit method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function edit($id = null) {
		if (!$this->VendorReview->exists($id)) {
			throw new NotFoundException(__('Invalid vendor review'));
		}
		if ($this->request->is(array('post', 'put'))) {
			if ($this->VendorReview->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor review has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor review could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('VendorReview.' . $this->VendorReview->primaryKey => $id));
			$this->request->data = $this->VendorReview->find('first', $options);
		}
		$customers = $this->VendorReview->Customer->find('list');
		$vendors = $this->VendorReview->Vendor->find('list');
		$this->set(compact('customers', 'vendors'));
	}

/**
 * delete method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function delete($id = null) {
		$this->VendorReview->id = $id;
		if (!$this->VendorReview->exists()) {
			throw new NotFoundException(__('Invalid vendor review'));
		}
		$this->request->allowMethod('post', 'delete');
		if ($this->VendorReview->delete()) {
			$this->Session->setFlash(__('The vendor review has been deleted.'));
		} else {
			$this->Session->setFlash(__('The vendor review could not be deleted. Please, try again.'));
		}
		return $this->redirect(array('action' => 'index'));
	}

/**
 * admin_index method
 *
 * @return void
 */
	public function admin_index() {
		$this->VendorReview->recursive = 0;
		$this->set('vendorReviews', $this->Paginator->paginate());
	}

/**
 * admin_view method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_view($id = null) {
		if (!$this->VendorReview->exists($id)) {
			throw new NotFoundException(__('Invalid vendor review'));
		}
		$options = array('conditions' => array('VendorReview.' . $this->VendorReview->primaryKey => $id));
		$this->set('vendorReview', $this->VendorReview->find('first', $options));
	}

/**
 * admin_add method
 *
 * @return void
 */
	public function admin_add() {
		if ($this->request->is('post')) {
			$this->VendorReview->create();
			if ($this->VendorReview->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor review has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor review could not be saved. Please, try again.'));
			}
		}
		$customers = $this->VendorReview->Customer->find('list');
		$vendors = $this->VendorReview->Vendor->find('list');
		$this->set(compact('customers', 'vendors'));
	}

/**
 * admin_edit method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_edit($id = null) {
		if (!$this->VendorReview->exists($id)) {
			throw new NotFoundException(__('Invalid vendor review'));
		}
		if ($this->request->is(array('post', 'put'))) {
			if ($this->VendorReview->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor review has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor review could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('VendorReview.' . $this->VendorReview->primaryKey => $id));
			$this->request->data = $this->VendorReview->find('first', $options);
		}
		$customers = $this->VendorReview->Customer->find('list');
		$vendors = $this->VendorReview->Vendor->find('list');
		$this->set(compact('customers', 'vendors'));
	}

/**
 * admin_delete method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_delete($id = null) {
		$this->VendorReview->id = $id;
		if (!$this->VendorReview->exists()) {
			throw new NotFoundException(__('Invalid vendor review'));
		}
		$this->request->allowMethod('post', 'delete');
		if ($this->VendorReview->delete()) {
			$this->Session->setFlash(__('The vendor review has been deleted.'));
		} else {
			$this->Session->setFlash(__('The vendor review could not be deleted. Please, try again.'));
		}
		return $this->redirect(array('action' => 'index'));
	}
}
