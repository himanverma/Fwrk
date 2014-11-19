<?php
App::uses('AppController', 'Controller');
/**
 * Addresses Controller
 *
 * @property Address $Address
 * @property PaginatorComponent $Paginator
 * @property SessionComponent $Session
 */
class AddressesController extends AppController {

/**
 * Components
 *
 * @var array
 */
	public $components = array('Paginator', 'Session');
        
        public function beforeFilter() {
            parent::beforeFilter();
            $this->Auth->allow(array('api_add'));
        }
        
        
        public function api_add(){
            if($this->request->is('post')){
                if($this->Address->save($this->request->data)){
                    $this->set(array(
                        'data' => 'success',
                        '_serialize' => array('data')
                    ));
                }else{
                    $this->set(array(
                        'data' => 'sorry',
                        '_serialize' => array('data')
                    ));
                }
            }
        }
}
