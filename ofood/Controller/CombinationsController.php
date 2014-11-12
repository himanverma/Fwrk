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
        
        
        
        public function beforeFilter() {
            parent::beforeFilter();
            $this->Auth->allow(array('api_index','api_view'));
        }
        
/*------------------------------------------ Web-Services-Start----------------------------------------*/
        public function api_index() {
            
            
//            $lat=$this->request->data['User']['latitude']=30.7238504;
//            $long=$this->request->data['User']['longitude']=76.8465098;
//            $count=$this->request->data['User']['count']=1;
            $lat=$this->request->data['User']['latitude'];
            $long=$this->request->data['User']['longitude'];
            $count=$this->request->data['User']['count'];
            $combination1 = $this->Combination->find('count',array(
                "fields" => array("get_d($lat,$long,Vendor.lat,Vendor.long) as distance","Vendor.*","Combination.*"),
                "order"=>'distance ASC',
            ));
            
            $combination = $this->Combination->find('all',array(
                "fields" => array("get_d($lat,$long,Vendor.lat,Vendor.long) as distance","Vendor.*","Combination.*"),
                "order"=>'distance ASC',
                'limit'=>8,
                'page' => $count
            ));
            $combination['list']=$combination1;
//            Debugger::log($combination);
            $this->set(array(
                'data' => $combination,
                '_serialize' => array('data')
            ));
        }
        
        public function api_view($id = null) {
		if (!$this->Combination->exists($id)) {
			throw new NotFoundException(__('Invalid Combination'));
		}
		$options = array('conditions' => array('Combination.' . $this->Combination->primaryKey => $id));
                $this->set(array(
                    'data' => $this->Combination->find('first', $options),
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
            $this->loadModel('Recipe');
            $this->Recipe->recursive = 0;
            $recipes = $this->Recipe->find('all');
            $r = array();
            foreach ($recipes as $d){
                $r[] = $d['Recipe']; 
            }
            $this->set("recipes", $r);
            
            $this->loadModel('Vendor');
            $this->Vendor->recursive = 0;
            $vendors = $this->Vendor->find('all');
            $r = array();
            foreach ($vendors as $d){
                $r[] = $d['Vendor']; 
            }
            $this->set("vendors", $r);
            if($this->request->is('post')){
                foreach($this->request->data as $data){
                    $this->Combination->saveAssociated($data, array('deep' => true));
                }
            }
            
        }
}
