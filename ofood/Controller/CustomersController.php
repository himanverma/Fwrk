<?php

App::uses('AppController', 'Controller');

/**
 * Customers Controller
 *
 * @property Customer $Customer
 * @property PaginatorComponent $Paginator
 * @property SessionComponent $Session
 */
class CustomersController extends AppController {

    /**
     * Components
     *
     * @var array
     */
    public $components = array('Paginator', 'Session');

    public function beforeFilter() {
        parent::beforeFilter();
        $this->Auth->allow(array('api_index'));
    }

/*------------------------------------------ Web-Services-Start----------------------------------------*/
        public function api_index() {
            $customers = $this->Customer->find('all');
            $this->set(array(
                'data' => $customers,
                '_serialize' => array('data')
            ));
        }
        
        public function api_view($id = null) {
		if (!$this->Customer->exists($id)) {
			throw new NotFoundException(__('Invalid Customer'));
		}
		$options = array('conditions' => array('Customer.' . $this->Customer->primaryKey => $id));
                $this->set(array(
                    'data' => $this->Customer->find('first', $options),
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
        $this->Customer->recursive = 0;
        $this->set('customers', $this->Paginator->paginate());
    }

    /**
     * view method
     *
     * @throws NotFoundException
     * @param string $id
     * @return void
     */
    public function view($id = null) {
        if (!$this->Customer->exists($id)) {
            throw new NotFoundException(__('Invalid customer'));
        }
        $options = array('conditions' => array('Customer.' . $this->Customer->primaryKey => $id));
        $this->set('customer', $this->Customer->find('first', $options));
    }

    /**
     * add method
     *
     * @return void
     */
    public function add() {
        if ($this->request->is('post')) {
            $this->Customer->create();
            if ($this->Customer->save($this->request->data)) {
                $this->Session->setFlash(__('The customer has been saved.'));
                return $this->redirect(array('action' => 'index'));
            } else {
                $this->Session->setFlash(__('The customer could not be saved. Please, try again.'));
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
        if (!$this->Customer->exists($id)) {
            throw new NotFoundException(__('Invalid customer'));
        }
        if ($this->request->is(array('post', 'put'))) {
            if ($this->Customer->save($this->request->data)) {
                $this->Session->setFlash(__('The customer has been saved.'));
                return $this->redirect(array('action' => 'index'));
            } else {
                $this->Session->setFlash(__('The customer could not be saved. Please, try again.'));
            }
        } else {
            $options = array('conditions' => array('Customer.' . $this->Customer->primaryKey => $id));
            $this->request->data = $this->Customer->find('first', $options);
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
        $this->Customer->id = $id;
        if (!$this->Customer->exists()) {
            throw new NotFoundException(__('Invalid customer'));
        }
        $this->request->allowMethod('post', 'delete');
        if ($this->Customer->delete()) {
            $this->Session->setFlash(__('The customer has been deleted.'));
        } else {
            $this->Session->setFlash(__('The customer could not be deleted. Please, try again.'));
        }
        return $this->redirect(array('action' => 'index'));
    }

    /**
     * admin_index method
     *
     * @return void
     */
    public function admin_index() {
        $this->Customer->recursive = 0;
        $this->set('customers', $this->Paginator->paginate());
    }

    /**
     * admin_view method
     *
     * @throws NotFoundException
     * @param string $id
     * @return void
     */
    public function admin_view($id = null) {
        if (!$this->Customer->exists($id)) {
            throw new NotFoundException(__('Invalid customer'));
        }
        $options = array('conditions' => array('Customer.' . $this->Customer->primaryKey => $id));
        $this->set('customer', $this->Customer->find('first', $options));
    }

    /**
     * admin_add method
     *
     * @return void
     */
    public function admin_add() {
        if ($this->request->is('post')) {
            $this->Customer->create();
            if ($this->Customer->save($this->request->data)) {
                $this->Session->setFlash(__('The customer has been saved.'));
                return $this->redirect(array('action' => 'index'));
            } else {
                $this->Session->setFlash(__('The customer could not be saved. Please, try again.'));
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
        if (!$this->Customer->exists($id)) {
            throw new NotFoundException(__('Invalid customer'));
        }
        if ($this->request->is(array('post', 'put'))) {
            if ($this->Customer->save($this->request->data)) {
                $this->Session->setFlash(__('The customer has been saved.'));
                return $this->redirect(array('action' => 'index'));
            } else {
                $this->Session->setFlash(__('The customer could not be saved. Please, try again.'));
            }
        } else {
            $options = array('conditions' => array('Customer.' . $this->Customer->primaryKey => $id));
            $this->request->data = $this->Customer->find('first', $options);
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
        $this->Customer->id = $id;
        if (!$this->Customer->exists()) {
            throw new NotFoundException(__('Invalid customer'));
        }
        $this->request->allowMethod('post', 'delete');
        if ($this->Customer->delete()) {
            $this->Session->setFlash(__('The customer has been deleted.'));
        } else {
            $this->Session->setFlash(__('The customer could not be deleted. Please, try again.'));
        }
        return $this->redirect(array('action' => 'index'));
    }

}
