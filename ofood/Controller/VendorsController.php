<?php
App::uses('AppController', 'Controller');
/**
 * Vendors Controller
 *
 * @property Vendor $Vendor
 * @property PaginatorComponent $Paginator
 * @property SessionComponent $Session
 */
class VendorsController extends AppController {

/**
 * Components
 *
 * @var array
 */
	public $components = array('Paginator', 'Session','Date');
        
        
        public function api_index(){
            $this->Vendor->recursive = 3;
            $vendors = $this->Vendor->find('all');
            $this->set(array(
                'data' => $vendors,
                '_serialize' => array('data')
            ));
        }
        
        public function api_view($id = null) {
		if (!$this->Vendor->exists($id)) {
			throw new NotFoundException(__('Invalid vendor'));
		}
		$options = array(
                    'recursive'=>2,
                    'contain'=>array(
                        'Combination',
                        'VendorReview'=>array(
                            'Customer'=>array('id','name','image')
                            )
                        ),
                    'conditions' => array('Vendor.' . $this->Vendor->primaryKey => $id)
                    );
                //$this->Vender->virtualFields['rating'] = 'SUM(`VendorReview`.`ratings`) / COUNT(`VendorReview`.`ratings`) * 5';
                $allRecords=$this->Vendor->find('first', $options);
                
                foreach ($allRecords['VendorReview'] as $allRecord){
                    $allRecord['created']=$this->Date->time_elapsed_string($allRecord['created']);
                    $allRecord1[]=$allRecord;
                }
                $allRecords['VendorReview']=$allRecord1;
                $this->set(array(
                    'data' => $allRecords,
                    '_serialize' => array('data')
                ));
	}

        
        /**
 * index method
 *
 * @return void
 */
	public function index() {
		$this->Vendor->recursive = 1;
		$this->set('vendors', $this->Paginator->paginate());
	}

/**
 * view method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function view($id = null) {
		if (!$this->Vendor->exists($id)) {
			throw new NotFoundException(__('Invalid vendor'));
		}
		$options = array('conditions' => array('Vendor.' . $this->Vendor->primaryKey => $id));
		$this->set('vendor', $this->Vendor->find('first', $options));
	}

/**
 * add method
 *
 * @return void
 */
	public function add() {
		if ($this->request->is('post')) {
			$this->Vendor->create();
			if ($this->Vendor->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor could not be saved. Please, try again.'));
			}
		}
	}

/**
 * edit method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function edit($id = null) {
		if (!$this->Vendor->exists($id)) {
			throw new NotFoundException(__('Invalid vendor'));
		}
		if ($this->request->is(array('post', 'put'))) {
			if ($this->Vendor->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('Vendor.' . $this->Vendor->primaryKey => $id));
			$this->request->data = $this->Vendor->find('first', $options);
		}
	}

/**
 * delete method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function delete($id = null) {
		$this->Vendor->id = $id;
		if (!$this->Vendor->exists()) {
			throw new NotFoundException(__('Invalid vendor'));
		}
		$this->request->allowMethod('post', 'delete');
		if ($this->Vendor->delete()) {
			$this->Session->setFlash(__('The vendor has been deleted.'));
		} else {
			$this->Session->setFlash(__('The vendor could not be deleted. Please, try again.'));
		}
		return $this->redirect(array('action' => 'index'));
	}

/**
 * admin_index method
 *
 * @return void
 */
	public function admin_index() {
		$this->Vendor->recursive = 0;
		$this->set('vendors', $this->Paginator->paginate());
	}

/**
 * admin_view method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_view($id = null) {
		if (!$this->Vendor->exists($id)) {
			throw new NotFoundException(__('Invalid vendor'));
		}
		$options = array('conditions' => array('Vendor.' . $this->Vendor->primaryKey => $id));
		$this->set('vendor', $this->Vendor->find('first', $options));
	}

/**
 * admin_add method
 *
 * @return void
 */
	public function admin_add() {
		if ($this->request->is('post')) {
			$this->Vendor->create();
			if ($this->Vendor->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor could not be saved. Please, try again.'));
			}
		}
	}

/**
 * admin_edit method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_edit($id = null) {
		if (!$this->Vendor->exists($id)) {
			throw new NotFoundException(__('Invalid vendor'));
		}
		if ($this->request->is(array('post', 'put'))) {
			if ($this->Vendor->save($this->request->data)) {
				$this->Session->setFlash(__('The vendor has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The vendor could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('Vendor.' . $this->Vendor->primaryKey => $id));
			$this->request->data = $this->Vendor->find('first', $options);
		}
	}

/**
 * admin_delete method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function admin_delete($id = null) {
		$this->Vendor->id = $id;
		if (!$this->Vendor->exists()) {
			throw new NotFoundException(__('Invalid vendor'));
		}
		$this->request->allowMethod('post', 'delete');
		if ($this->Vendor->delete()) {
			$this->Session->setFlash(__('The vendor has been deleted.'));
		} else {
			$this->Session->setFlash(__('The vendor could not be deleted. Please, try again.'));
		}
		return $this->redirect(array('action' => 'index'));
	}
}
