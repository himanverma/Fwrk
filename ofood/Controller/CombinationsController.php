<?php
App::uses('AppController', 'Controller');
/**
 * Combinations Controller
 *
 * @property Combination $Combination
 * @property PaginatorComponent $Paginator
 * @property SessionComponent $Session
 */
class CombinationsController extends AppController {

/**
 * Components
 *
 * @var array
 */
	public $components = array('Paginator', 'Session');

/**
 * index method
 *
 * @return void
 */
	public function index() {
		$this->Combination->recursive = 0;
		$this->set('combinations', $this->Paginator->paginate());
	}

/**
 * view method
 *
 * @throws NotFoundException
 * @param string $id
 * @return void
 */
	public function view($id = null) {
		if (!$this->Combination->exists($id)) {
			throw new NotFoundException(__('Invalid combination'));
		}
		$options = array('conditions' => array('Combination.' . $this->Combination->primaryKey => $id));
		$this->set('combination', $this->Combination->find('first', $options));
	}

/**
 * add method
 *
 * @return void
 */
	public function add() {
		if ($this->request->is('post')) {
			$this->Combination->create();
			if ($this->Combination->save($this->request->data)) {
				$this->Session->setFlash(__('The combination has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The combination could not be saved. Please, try again.'));
			}
		}
		$vendors = $this->Combination->Vendor->find('list');
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
		if (!$this->Combination->exists($id)) {
			throw new NotFoundException(__('Invalid combination'));
		}
		if ($this->request->is(array('post', 'put'))) {
			if ($this->Combination->save($this->request->data)) {
				$this->Session->setFlash(__('The combination has been saved.'));
				return $this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The combination could not be saved. Please, try again.'));
			}
		} else {
			$options = array('conditions' => array('Combination.' . $this->Combination->primaryKey => $id));
			$this->request->data = $this->Combination->find('first', $options);
		}
		$vendors = $this->Combination->Vendor->find('list');
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
		$this->Combination->id = $id;
		if (!$this->Combination->exists()) {
			throw new NotFoundException(__('Invalid combination'));
		}
		$this->request->allowMethod('post', 'delete');
		if ($this->Combination->delete()) {
			$this->Session->setFlash(__('The combination has been deleted.'));
		} else {
			$this->Session->setFlash(__('The combination could not be deleted. Please, try again.'));
		}
		return $this->redirect(array('action' => 'index'));
	}
        
        
        public function generate(){
            
        }
}
