<?php
App::uses('AppController','Controller');
/**
 * Users Controller
 *
 * @property User $User
 * @property PaginatorComponent $Paginator
 * @property SessionComponent $Session
 */
class UsersController extends AppController{
    
/**
 * Components
 *
 * @var array
 */
	public $components = array('Paginator', 'Session');
        
        public function beforeFilter() {
            parent::beforeFilter();
            $this->Auth->allow();
        }
        
        
        public function api_login(){
            $this->recursive = -1;
            if ($this->request->is('post')) {
                $check = $this->User->find('first', array('conditions' => array(
                    "OR" => array(
                                "User.username" => $this->request->data['User']['username']
                                ,"User.email" => $this->request->data['User']['username']
                            )
                    ),'fields'=>array('username'),'recursive'=>'-1'));
                    $this->request->data['User']['username'] = $check['User']['username'];
                if (!$this->Auth->login()) {
                    $response['error'] = '1';
                    $response['msg'] = 'User not valid';
                } else {
                    $user = $this->User->find('first', array('conditions' => array('id' => $this->Auth->user('id'))));
                    if ($user['User']['status'] == 0) {
                        $response['error'] = '1';
                        $response['msg'] = 'User not active';
                    } else {
                        $user['User']['image'] = FULL_BASE_URL . $this->webroot . 'files/profile_image/' . $user['User']['image'];
                        $this->User->id = $user['User']['id'];
                        $this->User->saveField('device_token', $this->request->data['User']['device_token']);
                        $response['error'] = '0';
                        $response['list'] = $user;
                    }
                }
                $this->set(array(
                    'data' => $response,
                    '_serialize' => array('data')
                ));
            }else{
                throw new BadRequestException("Undefined Request Method");
            }
            
        }
        public function api_registration() {
            Configure::write('debug',2);
            if ($this->request->is('post')) {
//                    $this->request->data['User']['email']="ajay_p@avainfotech.com";
                    if ($this->User->hasAny(array('User.username' => $this->request->data['User']['username']))) {
                        $response['error'] = '1';
                        $response['msg'] = 'Username already exist';
                    } else {
                        if ($this->User->hasAny(array('User.email' => $this->request->data['User']['email']))) {
                            $response['error'] = '1';
                            $response['msg'] = 'Email_id already exist';
                        } else {
                            $this->User->create();
                            if($this->request->data['User']['photo']){
                                $one=$this->request->data['User']['photo'];
                                if($one['error']==0){
                                    $ext = pathinfo($one['name'], PATHINFO_EXTENSION);
                                    $image_name=$this->request->data['User']['photo']=date('YmdHis').".".$ext;
                                    $pth="files".DS."profile_image".DS.$image_name;
                                    move_uploaded_file($one['tmp_name'], $pth);
                                }
                            }
                            if($this->request->data['User']['id_proof']){
                                $one1=$this->request->data['User']['id_proof'];
                                if($one1['error']==0){
                                    $ext1 = pathinfo($one1['name'], PATHINFO_EXTENSION);
                                    $image_name1=$this->request->data['User']['id_proof']=date('YdmHis').".".$ext1;
                                    $pth1="files".DS."id_proof".DS.$image_name1;
                                    move_uploaded_file($one1['tmp_name'], $pth1);
                                }
                            }
                            if ($this->User->save($this->request->data)) {
                                $verify_id = base64_encode($this->User->getLastInsertID());
                                $url=FULL_BASE_URL . $this->webroot . "users/verify/" . $verify_id;
                                $ms="Welcome to Office-Ride 
                                    <b><a href='" . $url . "' style='text-decoration:none'>Click to verify your email.</a></b><br/>";
                                $l= new CakeEmail('smtp');
                                $l->emailFormat('html')->template('default','default')->subject('Registration Successfully!!!')->
                                        to($this->request->data['User']['email'])->send($ms);
                                $response['error'] = '0';
                                $response['msg'] = 'Register successfully';
                            } else {
                                $response['error'] = '1';
                                $response['msg'] = 'Sorry please try again';
                                
                            }
		        }
                    }
                    $this->set(array(
                        'data' => $response,
                        '_serialize' => array('data')
                    ));
	       }else{
                    throw new BadRequestException("Undefined Request Method");
               }
        }
        
        public function api_userInfo($id=NULL){
            $userInfo=$this->User->find('first',array('conditions'=>array('User.id'=>$id)));
            if($userInfo){
                $this->set(array(
                    'data' => $userInfo,
                    '_serialize' => array('data')
                ));
            }else{
                $this->set(array(
                    'data' => "Sorry no record found!!!",
                    '_serialize' => array('data')
                ));
            }
        }
}
