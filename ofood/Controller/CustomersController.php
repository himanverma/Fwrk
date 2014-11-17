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
        $this->Auth->allow(array('api_index', 'api_add'));
    }

    public function api_index() {
        $customers = $this->Customer->find('all');
        $this->set(array(
            'data' => $customers,
            '_serialize' => array('data')
        ));
    }

    public function api_add() {
        Configure::write('debug',0);
        if ($this->request->is('post')) {
            $customerRcord = $this->Customer->find('first', array(
                'conditions' => array(
                    "OR" => array(
                        "Customer.mobile_number" => $this->request->data['Customer']['mobile_number'],
                        "Customer.fbid" => $this->request->data['Customer']['fbid'],
                    )
                )
            ));
            if (!empty($customerRcord)) {
                $this->Customer->updateAll(array(
                    'Customer.deviceId' => "'" . $this->request->data['Customer']['deviceId'] . "'"
                        ), array(
                    'Customer.id' => $customerRcord['Customer']['id']
                ));
                $this->set(array(
                    'data' => $customerRcord['Customer']['id'],
                    '_serialize' => array('data')
                ));
            } else {
                if ($this->Customer->save($this->request->data)) {
                    /* send this v_code to mail and sms for verification */
                    $v_code = strtoupper(bin2hex(decbin($this->Customer->getLastInsertID())));

                    $this->Customer->updateAll(array(
                        'Customer.v_code' => "'" . $v_code . "'"
                            ), array(
                        "Customer.id" => $this->Customer->getLastInsertID()
                    ));
                    $this->set(array(
                        'data' => $this->Customer->getLastInsertID(),
                        '_serialize' => array('data')
                    ));
                } else {
                    $this->set(array(
                        'data' => 'sorry',
                        '_serialize' => array('data')
                    ));
                }
            }
        }
    }
    
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
